import { useEffect, useMemo, useState } from "react";
import type { Article } from "../types/Article";
import "./css/ArticleList.css";
import type { ArticleFilter } from "../types/ArticleFilter";
import { getArticlesByBranch, getArticlesByFilters } from "../services/articlesService";

interface Props {
  articles: Article[];
  onEdit: (article: Article) => void;
  onView: (article: Article) => void;
  onDelete: (id: number) => void;
}

export default function ArticleList({ articles, onEdit, onView, onDelete }: Props) {
  const [search, setSearch] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(5);

  const [mode, setMode] = useState<"local" | "services">("local");

  const DEFAULT_BRANCH = "Medellin";
  const [serviceArticles, setServiceArticles] = useState<Article[]>([]);
  const [branchQuery, setBranchQuery] = useState("");
  const [filterTitle, setFilterTitle] = useState("");
  const [filterAuthor, setFilterAuthor] = useState("");
  const [loading, setLoading] = useState(false);
  const [serviceError, setServiceError] = useState<string | null>(null);

  const sourceArticles = mode === "local" ? articles : serviceArticles;

  const filteredArticles = useMemo(() => {
    const lowerSearch = search.toLowerCase();
    if (!sourceArticles || sourceArticles.length === 0) return [];
    return sourceArticles.filter(
      (a) =>
        (a.title || "").toLowerCase().includes(lowerSearch) ||
        (a.author || "").toLowerCase().includes(lowerSearch)
    );
  }, [search, sourceArticles]);

  const totalPages = Math.ceil(filteredArticles.length / itemsPerPage) || 1;

  useEffect(() => {
    if (currentPage > totalPages) setCurrentPage(1);
  }, [totalPages]);

  useEffect(() => {
    if (mode === "services") {
      setBranchQuery(DEFAULT_BRANCH);
      fetchByBranch(DEFAULT_BRANCH);
    }
  }, [mode]);

  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentArticles = filteredArticles.slice(
    startIndex,
    startIndex + itemsPerPage
  );

  const handlePageChange = (page: number) => {
    if (page >= 1 && page <= totalPages) setCurrentPage(page);
  };

  const handleItemsPerPageChange = (
    e: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setItemsPerPage(Number(e.target.value));
    setCurrentPage(1);
  };

  const fetchByBranch = async (branch: string) => {
    setLoading(true);
    setServiceError(null);
    console.debug("fetchByBranch -> branch:", branch);
    try {
      const res = await getArticlesByBranch(branch);
      console.debug("fetchByBranch -> response:", res);
      if (!res) {
        setServiceError("No response from server");
        setServiceArticles([]);
      } else if (!res.success) {
        setServiceError(res.message || "Error fetching by branch");
        setServiceArticles([]);
      } else {
        const body = res.body;
        const arr = Array.isArray(body) ? body : body ? [body] : [];
        setServiceArticles(arr);
        console.debug("fetchByBranch -> saved articles:", arr);
      }
    } catch (err) {
      console.error("fetchByBranch error", err);
      setServiceError("Error fetching by branch");
      setServiceArticles([]);
    } finally {
      setLoading(false);
      setCurrentPage(1);
    }
  };

  const fetchByFilters = async () => {
    setLoading(true);
    setServiceError(null);
    setMode("services");
    try {
      const filters: ArticleFilter = { title: filterTitle, author: filterAuthor } as any;
      const res = await getArticlesByFilters(filters);
      if (!res.success) {
        setServiceError(res.message || "Error fetching by filters");
        setServiceArticles([]);
      } else {
        const body = res.body as unknown;
        setServiceArticles(Array.isArray(body) ? (body as Article[]) : body ? [body as Article] : []);
      }
    } catch {
      setServiceError("Error fetching by filters");
      setServiceArticles([]);
    } finally {
      setLoading(false);
      setCurrentPage(1);
    }
  };

  return (
    <div className="article-list">
      <h2 className="title">Articles</h2>

      <div className="article-list-header">
        <div style={{ display: "flex", gap: 8 }}>
          <button onClick={() => setMode("local")} disabled={mode === "local"}>
            Articles
          </button>
          <button onClick={() => setMode("services")} disabled={mode === "services"}>
            Search articles
          </button>
        </div>

        <input
          type="text"
          placeholder="Search by title or author"
          value={search}
          onChange={(e) => {
            setSearch(e.target.value);
            setCurrentPage(1);
          }}
          className="search-input"
        />

        <div className="pagination-controls">
          <label>Items per page:</label>
          <select value={itemsPerPage} onChange={handleItemsPerPageChange}>
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
          </select>
        </div>
      </div>

      {mode === "services" && (
        <div className="filters-container">
          <div style={{ display: "flex", gap: 8, alignItems: "center", flexWrap: "wrap" }}>
            <div>
              <label>Branch:</label>
              <input
                value={branchQuery}
                onChange={(e) => setBranchQuery(e.target.value)}
                placeholder="e.g. Medellin"
                style={{ marginLeft: 8 }}
              />
              <button onClick={() => fetchByBranch(branchQuery)} disabled={loading} style={{ marginLeft: 8 }}>
                Fetch by Branch
              </button>
            </div>

            <div>
              <label>Title:</label>
              <input
                value={filterTitle}
                onChange={(e) => setFilterTitle(e.target.value)}
                placeholder="Title filter"
                style={{ marginLeft: 8 }}
              />
              <label style={{ marginLeft: 8 }}>Author:</label>
              <input
                value={filterAuthor}
                onChange={(e) => setFilterAuthor(e.target.value)}
                placeholder="Author filter"
                style={{ marginLeft: 8 }}
              />
              <button onClick={fetchByFilters} disabled={loading} style={{ marginLeft: 8 }}>
                Fetch by Filters
              </button>
            </div>

            {loading && <div>Loading...</div>}
            {serviceError && <div style={{ color: "red" }}>{serviceError}</div>}
          </div>
        </div>
      )}

      <div className="table-wrapper">
        {currentArticles.length === 0 ? (
          <p>No articles found.</p>
        ) : (
          <table className="styled-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {currentArticles.map((a) => (
                <tr key={a.id}>
                  <td>{a.title}</td>
                  <td>{a.author}</td>
                  <td>
                    <button onClick={() => onView(a)}>View</button>

                    {mode === "local" && (
                      <>
                        <button onClick={() => onEdit(a)}>Edit</button>
                        <button onClick={() => a.id && onDelete(a.id)}>Delete</button>
                      </>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {totalPages > 1 && (
        <div className="pagination">
          <button
            disabled={currentPage === 1}
            onClick={() => handlePageChange(1)}
          >
            First
          </button>

          <button
            disabled={currentPage === 1}
            onClick={() => handlePageChange(currentPage - 1)}
          >
            Prev
          </button>

          <span>
            Page {currentPage} of {totalPages}
          </span>

          <button
            disabled={currentPage === totalPages}
            onClick={() => handlePageChange(currentPage + 1)}
          >
            Next
          </button>

          <button
            disabled={currentPage === totalPages}
            onClick={() => handlePageChange(totalPages)}
          >
            Last
          </button>
        </div>
      )}
    </div>
  );
}
