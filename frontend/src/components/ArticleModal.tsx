import { useState } from "react";
import type { Article } from "../types/Article";
import "./css/ArticleModal.css";

interface Props {
  mode: "view" | "edit" | "create";
  article: Article | null;
  onSubmit?: (article: Article) => void;
  onClose: () => void;
}

export default function ArticleModal({ mode, article, onSubmit, onClose }: Props) {
  const [formData, setFormData] = useState<Article>({
    id: article?.id,
    title: article?.title || "",
    author: article?.author || "",
    content: article?.content || "",
    publishedDate: article?.publishedDate || "",
  });

  const readOnly = mode === "view";

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (onSubmit) onSubmit(formData);
  };

  return (
    <div className="modal-backdrop">
      <div className="modal-container">
        <h2 className="modal-title">
          {mode === "create"
            ? "Create Article"
            : mode === "edit"
            ? "Edit Article"
            : "View Article"}
        </h2>

        <form onSubmit={handleSubmit} className="article-form">
          <label>Title</label>
          <input
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
            readOnly={readOnly}
          />

          <label>Author</label>
          <input
            name="author"
            value={formData.author}
            onChange={handleChange}
            required
            readOnly={readOnly}
          />

          <label>Content</label>
          <input
            name="content"
            value={formData.content}
            onChange={handleChange}
            readOnly={readOnly}
          />

          <label>Published Date</label>
          <input
            type="date"
            name="publishedDate"
            value={formData.publishedDate}
            onChange={handleChange}
            readOnly={readOnly}
          />

          <div>
            {mode !== "view" && <button type="submit">Save</button>}
            <button type="button" onClick={onClose}>Close</button>
          </div>
        </form>
      </div>
    </div>
  );
}
