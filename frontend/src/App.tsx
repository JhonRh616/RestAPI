import { useEffect, useRef, useState } from "react";
import type { Article } from "./types/Article";
import ArticleList from "./components/ArticleList";
import ArticleModal from "./components/ArticleModal";
import { ToastProvider, useToast } from "./context/ToastContext";
import Toast from "./components/Toast";
import { createArticle, deleteArticle, getAllArticles, updateArticle } from "./services/articlesService";

function AppContent() {
  const [articles, setArticles] = useState<Article[]>([]);
  const [selected, setSelected] = useState<Article | null>(null);
  const [mode, setMode] = useState<"view" | "edit" | "create" | null>(null);
  const { showToast } = useToast();
  const loaded = useRef(false);

  useEffect(() => {
    if (loaded.current) return;
    loaded.current = true;
    loadArticles();
  }, []);

  const loadArticles = async () => {
    const data = await getAllArticles();
    showToast(data.message, data.success ? "success" : "error");
    setArticles(data.body);
  };

  const handleSubmit = async (article: Article) => {
    try {
      if (mode === "create") {
        const response = await createArticle(article);
        showToast(response.message, response.success ? "success" : "error");

        if (response.success) {
          setArticles((prev) => [...prev, response.body]);
        }
      }

      if (mode === "edit" && article.id) {
        const response = await updateArticle(article.id, article);
        showToast(response.message, response.success ? "success" : "error");

        if (response.success) {
          setArticles((prev) =>
            prev.map((a) => (a.id === article.id ? response.body : a))
          );
        }
      }

      setSelected(null);
      setMode(null);
    } catch {
      showToast("Connection error. Please try again later", "error");
    }
  };

  const handleDelete = async (id: number) => {
    try {
      const response = await deleteArticle(id);
      showToast(response.message, response.success ? "success" : "error");

      if (response.success) {
        setArticles((prev) => prev.filter((a) => a.id !== id));
      }
    } catch {
      showToast("Connection error. Please try again later", "error");
    }
  };

  return (
    <>
      <ArticleList
        articles={articles}
        onView={(a) => {
          setSelected(a);
          setMode("view");
        }}
        onEdit={(a) => {
          setSelected(a);
          setMode("edit");
        }}
        onDelete={handleDelete}
      />

      <button className="button-add" onClick={() => setMode("create")}>
        + Add Article
      </button>

      {mode && (
        <ArticleModal
          mode={mode}
          article={selected}
          onSubmit={handleSubmit}
          onClose={() => {
            setMode(null);
            setSelected(null);
          }}
        />
      )}
    </>
  );
}

export default function App() {
  return (
    <ToastProvider>
      <AppContent />
      <Toast />
    </ToastProvider>
  );
}
