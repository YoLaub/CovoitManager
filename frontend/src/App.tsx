import { BrowserRouter as Router, Link, Routes, Route, Navigate } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import MyCalendar from "./components/CalendarWithPresence.tsx";
import { Calendar, LayoutDashboard } from "lucide-react"; // ✅ icônes

function App() {
    return (
        <Router>
            <div className="min-h-screen flex flex-col">
                {/* Header fixe */}
                <header className="fixed top-0 left-0 w-full bg-white shadow-md z-10">
                    <nav className="max-w-6xl mx-auto p-4 flex space-x-8">
                        <Link
                            to="/calendar"
                            className="flex items-center gap-2 text-blue-600 font-medium hover:underline"
                        >
                            <Calendar size={18} /> Calendrier
                        </Link>
                        <Link
                            to="/dashboard"
                            className="flex items-center gap-2 text-blue-600 font-medium hover:underline"
                        >
                            <LayoutDashboard size={18} /> Dashboard
                        </Link>
                    </nav>
                </header>

                {/* Contenu */}
                <main className="flex-col m-24">
                    <Routes>
                        <Route path="/" element={<Navigate to="/calendar" replace />} />
                        <Route path="/calendar" element={<MyCalendar />} />
                        <Route path="/dashboard" element={<Dashboard />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;
