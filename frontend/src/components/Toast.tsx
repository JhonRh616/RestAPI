import { useToast } from "../context/ToastContext";
import "./css/Toast.css";

export default function Toast() {
  const { toasts, removeToast } = useToast();

  return (
    <div className="toast-container">
      {toasts.map((t) => (
        <div key={t.id} className={`toast ${t.type}`}>
          <span>{t.message}</span>
          <button onClick={() => removeToast(t.id)}>x</button>
        </div>
      ))}
    </div>
  );
}
