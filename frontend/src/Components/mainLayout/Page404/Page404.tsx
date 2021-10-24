import "./Page404.css";
import page404 from "../../../Assets/page404.png"
import { useHistory } from "react-router";
import Button from '@material-ui/core/Button';
import { ArrowBackIosOutlined } from "@material-ui/icons";

function Page404(): JSX.Element {
    let history = useHistory();

    function backToHome(){
        history.push("/home")
    }
    
    return (
        <div className="Page404" style={{
            backgroundImage: `url(${page404})`,
            backgroundPosition: 'center',
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat'
        }}>
            <br/><br/>
            <div className="returnButton">
                <Button size="small" variant="outlined" startIcon={<ArrowBackIosOutlined/>} color="secondary" onClick={backToHome}>
                    Back to Homepage
                </Button>
            </div>
        </div>
    );
}

export default Page404;
