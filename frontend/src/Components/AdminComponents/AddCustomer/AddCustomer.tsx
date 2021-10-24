import "./AddCustomer.css";
import {Button, ButtonGroup, TextField} from "@material-ui/core";
import Customer from "../../models/Customer";
import { useHistory } from "react-router";
import { useForm } from "react-hook-form";
import store from "../../Redux/store";
import notify from "../../Util/Notify";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import { Email, PersonOutlineRounded, PersonRounded } from "@material-ui/icons";
import LockOpenIcon from '@material-ui/icons/LockOpen';

function AddCustomer(): JSX.Element {
    const history = useHistory();
    const {register, handleSubmit, formState: { errors }} = useForm<Customer>();
    
    function send(customer: Customer){
        if(store.getState().authState.loginUser.clientType !== "ADMINISTRATOR"){
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        }
        jwtAxios.post(globals.urls.administrator + "addCustomer", customer).then((response) => {
            notify.success("Customer was added!");
        }).catch (error => {
            notify.error(error.response.data)
        })
    }

    return (
        <div className="AddCustomer smallBox">
			<h2>Add new Customer</h2><hr/>
            <form onSubmit={handleSubmit(send)}>
                <PersonOutlineRounded style={{fontSize:40, margin :10}}/>
                <TextField label="First Name" variant="outlined" helperText="Required"
                         {...register("firstName",{
                             required: {value:true, message: "Required field"},
                             minLength: {value:1, message: "min length is 1"}
                         })}/>
                <br/>
                <span>{errors.firstName && <p>{errors.firstName.message}</p>}</span>
                <br/>
                <PersonRounded style={{fontSize:40, margin :10}}/>
                <TextField label="Last Name" variant="outlined" helperText="Required"
                         {...register("lastName",{
                             required: {value:true, message: "Required field"},
                             minLength: {value:1, message: "min length is 1"}
                         })}/>
                <br/>
                <span>{errors.lastName && <p>{errors.lastName.message}</p>}</span>
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
        </div>
    );
}

export default AddCustomer;
