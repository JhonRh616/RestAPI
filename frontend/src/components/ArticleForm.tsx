import { useState, useEffect } from "react";
import type { FormEvent } from "react";
import type { Article } from "../types/Article";

interface Props {
  onSubmit: (article: Article) => void;
  initialData?: Article | null;
}

export default function ArticleForm({ onSubmit, initialData }: Props) {
  const [formData, setFormData] = useState<Article>({
    author: "",
    title: "",
    content: "",
    publishedDate: "",
  });

  useEffect(() => {
    if (initialData) setFormData(initialData);
  }, [initialData]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="form">
      <input
        name="author"
        value={formData.author}
        onChange={handleChange}
        placeholder="Author"
        required
      />
      <input
        name="title"
        value={formData.title}
        onChange={handleChange}
        placeholder="Title"
        required
      />
      <textarea
        name="content"
        value={formData.content}
        onChange={handleChange}
        placeholder="Content"
      />
      <input
        type="date"
        name="publishedDate"
        value={formData.publishedDate}
        onChange={handleChange}
      />
      <button type="submit">Save</button>
    </form>
  );
}
