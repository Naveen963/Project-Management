import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext"

const PrivateRoute = ({ children, allowedRoles = [] }) => {

    const { user, loading } = useAuth();

    if (loading) return <p>Loading...</p>

    if (!user)
        return <Navigate to="/signin" replace />

    if (allowedRoles.length && !allowedRoles.includes(user.role)) {
        return <Navigate to="/" replace />
    }
}

export default PrivateRoute