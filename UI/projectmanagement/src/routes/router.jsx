import { createBrowserRouter, createRoutesFromElements, Route } from "react-router-dom";
import SignIn from "../pages/SignIn";
import PrivateRoute from "./PrivateRoute";
import Dashboard from "../pages/Dashboard";

const router = createBrowserRouter(
    createRoutesFromElements(
        <>
            <Route path="/signin" element={<SignIn />} />
            <Route path="/" element={
                <PrivateRoute>
                    <Dashboard />
                </PrivateRoute>
            } />
        </>
    )
)

export default router