import { useEffect } from "react";
import { useHistory } from "react-router";
import { logoutUserAction } from "../../Redux/AuthState";
import store from "../../Redux/store";
import "./Logout.css";

function Logout(): JSX.Element {
    let history = useHistory();

    useEffect(()=>{
        store.dispatch(logoutUserAction());
        history.push("/home")
    })
    
    return (
        <div className="Logout">
        </div>
    )
}

export default Logout;
