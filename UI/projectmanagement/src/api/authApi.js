import api from "./axiosConfig";

export const signIn = async (data) => {
  const response = await api.post("/auth/signin", data);
  return response.data;
};
