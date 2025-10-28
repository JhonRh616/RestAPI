import { useMemo, useState } from "react";
import type { Article } from "../types/Article";
import "./css/ArticleList.css";

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

  const filteredArticles = useMemo(() => {
    const lowerSearch = search.toLowerCase();
    if (!articles) return [];
    return articles.filter(
      (a) =>
        a.title.toLowerCase().includes(lowerSearch) ||
        a.author.toLowerCase().includes(lowerSearch)
    );
  }, [search, articles]);

  const totalPages = Math.ceil(filteredArticles.length / itemsPerPage) || 1;

  if (currentPage > totalPages) setCurrentPage(1);

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

  return (
    <div className="article-list">
      <h2 className="title">Articles</h2>

      <div className="article-list-header">
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
                    <button onClick={() => onEdit(a)}>Edit</button>
                    <button onClick={() => a.id && onDelete(a.id)}>Delete</button>
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
