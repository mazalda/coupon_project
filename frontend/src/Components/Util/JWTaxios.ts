import axios from "axios";
import { loginUserRegistertAction, logoutUserAction } from "../Redux/AuthState";
import store from "../Redux/store";
import notify from "./Notify";

const jwtAxios = axios.create();

jwtAxios.interceptors.request.use(request=>{
    request.headers = {
        "Authorization" : store.getState().authState.loginUser.token
    }
    return request;
});

jwtAxios.interceptors.response.use(response=>{
    store.getState().authState.loginUser.token = response.headers.authorization;
    store.dispatch(loginUserRegistertAction(response.headers.authorization));
    return response;
})

export default jwtAxios;