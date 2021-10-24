import { useForm } from "react-hook-form";
import "./Login.css";
import { Button, ButtonGroup, MenuItem, Select, TextField, Typography } from "@material-ui/core";
import { AccountBox } from "@material-ui/icons";
import LockOpenIcon from '@material-ui/icons/LockOpen';
import AccessibilityNewIcon from '@material-ui/icons/AccessibilityNew';
import UserDetails from "../../models/userDetails";
import globals from "../../Util/Globals";
import { useHistory } from "react-router";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";

function Login(): JSX.Element {
    const {register, handleSubmit, formState: {errors}} = useForm<UserDetails>();
    const history = useHistory();

    function getUrl(userDetails:UserDetails):string{
        let myUrl:string="";
        if (userDetails.clientType === "ADMINISTRATOR"){
             return myUrl = globals.urls.administrator;
        } else if (userDetails.clientType === "COMPANY"){
             return myUrl = globals.urls.company;
        } else if (userDetails.clientType === "CUSTOMER"){
             return myUrl = globals.urls.customer;
        }
        return "/";
    }

    function sendToPage(userDetails: UserDetails){
        if (userDetails.clientType === "ADMINISTRATOR"){
            return history.push("/Admin/home");
       } else if (userDetails.clientType === "COMPANY"){
            return history.push("/Company/home");
       } else if (userDetails.clientType === "CUSTOMER"){
            return history.push("/Customer/");
       }
       return history.push("/login");
    }

    function send(userDetails:UserDetails){
        jwtAxios.post(getUrl(userDetails) + "login", userDetails)
        .then((response)=>{
            notify.success("You have been succesfully logged in");
            sendToPage(userDetails);
        })
        .catch(error => {
            notify.error(error.response.data);
        })
    }

    return (
        <div className="Login">
			<div>
                <br/>
                <form onSubmit={handleSubmit(send)}>
                    <Typography variant="h4" className="HeadLine">Login</Typography><br/>
                    <AccountBox style={{fontSize:40, margin :10}}/>
                    <TextField label="User Email" variant="outlined" type="email" helperText="Required"
                        {...register("email",{
                            required : {value : true, message : "Required field"}
                        })}/>
                    <span> {errors.email && <p>{errors.email.message}</p>}</span>
                    <br/><br/>
                    <LockOpenIcon style={{fontSize:40, margin :10}}/>
                    <TextField label="Password" variant="outlined" type="password" helperText="Required"
                        {...register("password",{
                            required : {value: true, message:"Required field"}, 
                            minLength:3, maxLength:10})}/>
                    <br/><br/>
                    <AccessibilityNewIcon style={{fontSize:40, margin:10}}/>
                    <Select style={{width:250}} {...register("clientType",{
                        required : {value: true, message:"Required field"}})}>
                        <MenuItem value={"ADMINISTRATOR"}>Administrator</MenuItem>
                        <MenuItem value={"COMPANY"}>Company</MenuItem>
                        <MenuItem value={"CUSTOMER"}>Customer</MenuItem>
                    </Select>
                    <span> {errors.clientType && <p>{errors.clientType.message}</p>}</span>
                    <br/><br/>
                    <ButtonGroup variant="contained">
                        <Button type="submit" color="secondary">login</Button>                    
                    </ButtonGroup>
                </form>
                <br/><br/>
            </div>
        </div>
    );
}

export default Login;
