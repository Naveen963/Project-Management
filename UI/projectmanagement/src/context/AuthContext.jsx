import { createContext, useContext, useEffect, useState } from "react";
import api from "../api/axiosConfig";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [user, setUser] = useState(null);
    const [loading, setIsLoading] = useState(true);

    useEffect(() => {
        api.get("/auth/user")
            .then(res => setUser(res.data))
            .catch(() => setUser(null))
            .finally(() => setIsLoading(false))
    })

    return (
        <AuthContext.Provider value={{ user, setUser, loading }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext)