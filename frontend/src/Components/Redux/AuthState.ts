import OnlineUser from "../models/OnlineUser";
import jwtDecode from "jwt-decode";

export class AuthState {
    public loginUser: OnlineUser = new OnlineUser();
}

export enum AuthActionType {
    LogoutUser = "LogoutUser",
    LoginUserRegister = "LoginUserRegister"
}

export interface AuthAction {
    type: AuthActionType,
    payload?: any
}

export function logoutUserAction(): AuthAction {
    return {type: AuthActionType.LogoutUser, payload: null}
}

export function loginUserRegistertAction(token: string): AuthAction {
    return {type: AuthActionType.LoginUserRegister, payload: token}
}

export function authReducer(currentState: AuthState = new AuthState(), action: AuthAction): AuthState {
    const newState = { ...currentState };

    switch (action.type) {
        case AuthActionType.LogoutUser:
            newState.loginUser = new OnlineUser();
            localStorage.removeItem("token");
            localStorage.removeItem("clientType");
            localStorage.removeItem("userId");
        break;
        case AuthActionType.LoginUserRegister:
            newState.loginUser.token = action.payload;
            localStorage.setItem("token", newState.loginUser.token)

            newState.loginUser.clientType =jwtDecode<OnlineUser>(newState.loginUser.token).clientType;
            localStorage.setItem("clientType",newState.loginUser.clientType);

            newState.loginUser.userId =jwtDecode<OnlineUser>(newState.loginUser.token).userId;
            localStorage.setItem("userId",newState.loginUser.userId.toString());

            // if(newState.loginUser.token ==)
        break;
    }

    return newState;
}