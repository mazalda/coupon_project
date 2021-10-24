import { Button, ButtonGroup, TextField} from "@material-ui/core";
import { useForm } from "react-hook-form";
import { useHistory } from "react-router";
import Company from "../../models/Company";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import { AccountBox, Email } from "@material-ui/icons";
import LockOpenIcon from '@material-ui/icons/LockOpen';

import "./AddCompany.css";

function AddCompany(): JSX.Element {
    const history = useHistory();
    const {register, handleSubmit, formState: { errors }} = useForm<Company>();

    function send(company: Company){
        if(store.getState().authState.loginUser.clientType !== "ADMINISTRATOR"){
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.administrator + "addCompany", company).then((response) => {
                notify.success("Company was added!");
                history.push("/Admin/home");
            }).catch(error =>{
                notify.error(error.response.data);
            })
        }
    }

    return (
        <div className="AddCompany smallBox">
			<h2>Add new Company</h2><hr/>
            <form onSubmit={handleSubmit(send)}>
                <AccountBox style={{fontSize:40, margin :10}}/>
                <TextField label="Name" variant="outlined" helperText="Required"
                         {...register ("name", {
                             required: {value:true, message: "Required field"},
                             minLength: {value:2, message: "min length is 2"}
                         })}/>
                <br/>
                <span>{errors.name && <p>{errors.name.message}</p>}</span>
                <br/>
                <Email style={{fontSize:40, margin: 10}}/>
                <TextField label="Email" variant="outlined" helperText="Required"
                        {...register("email", {
                            required: {value:true, message: "Required field"},
                            minLength: {value:2, message: "min length is 2"}
                        })}/>
                <br/>
                <span>{errors.email && <p>{errors.email.message}</p>}</span>
                <br/>
                <LockOpenIcon style={{fontSize:40, margin :10}}/>
                <TextField label="Password" variant="outlined" helperText="Required"
                        {...register("password", {
                            required: {value:true, message: "Required field"},
                            minLength: {value:2, message: "min length is 2"}
                        })}/>
                <br/>
                <span>{errors.password && <p>{errors.password.message}</p>}</span>
                <br/>
                <ButtonGroup variant="contained" fullWidth>
                        <Button type="submit" color="secondary">Add</Button>
                </ButtonGroup>
            </form>
            <br/>
        </div>
    );
}

export default AddCompany;


