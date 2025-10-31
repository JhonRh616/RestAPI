import type { Article } from "../types/Article";
import type { ApiGeneralResponse } from "../types/ApiGeneralResponse";
import type { ArticleFilter } from "../types/ArticleFilter";

const API_URL = "http://localhost:8080/api";
const ARTICLES_URL = `${API_URL}/articles`;
const SEARCH_URL = `${API_URL}/search`;

async function handleResponse<T>(res: Response): Promise<ApiGeneralResponse<T>> {
  try {
    const data = (await res.json()) as ApiGeneralResponse<T>;

    if (!res.ok || !data.success) {
      return { message: data.message, success: false, body: data.body ? data.body : null as any};
    }
    
    return data;
  } catch {
    return { success: false, message: "Server error parsing response", body: null as any };
  }
}

async function safeFetch<T>(url: string, options?: RequestInit): Promise<ApiGeneralResponse<T>> {
  try {
    const res = await fetch(url, options);
    return await handleResponse<T>(res);
  } catch {
    return { success: false, message: "Cannot connect to server", body: null as any };
  }
}

export const getAllArticles = () => safeFetch<Article[]>(ARTICLES_URL);
export const getArticleById = (id: number) => safeFetch<Article>(`${ARTICLES_URL}/${id}`);

export const createArticle = async (article: Article) =>
  safeFetch<Article>(ARTICLES_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(article),
  });

export const updateArticle = async (id: number, article: Article) =>
  safeFetch<Article>(`${ARTICLES_URL}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(article),
  });

export const deleteArticle = async (id: number) =>
  safeFetch(`${ARTICLES_URL}/${id}`, {
    method: "DELETE",
  });

//Search Articles
export const getArticlesByBranch = async (branch: string) => {
  const url = `${SEARCH_URL}?branch=${encodeURIComponent(branch)}`;
  return await safeFetch<Article[]>(url);
};


export const getArticlesByFilters = async (filers: ArticleFilter) => 
  safeFetch<Article>(SEARCH_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(filers),
  });
