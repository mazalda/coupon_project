import { Button, ButtonGroup, createStyles, FormHelperText, makeStyles, MenuItem, Select, TextField, Theme } from "@material-ui/core";
import { Add, DescriptionOutlined, Image, LocalOffer, TitleRounded} from "@material-ui/icons";
import { useForm } from "react-hook-form";
import { useHistory } from "react-router-dom";
import Coupon from "../../models/Coupon";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import "./AddCoupon.css";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
    },
    textField: {
      marginLeft: theme.spacing(1),
      marginRight: theme.spacing(1),
      width: 250,
    },
  }),
);

function AddCoupon(): JSX.Element {
    const classes = useStyles();
    const {register, handleSubmit, formState: { errors }} = useForm<Coupon>();
    const history = useHistory();
    
    function send(coupon: Coupon){
        if(store.getState().authState.loginUser.clientType !== "COMPANY"){
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            coupon.companyId = store.getState().authState.loginUser.userId;
            console.log("companyId is: " + store.getState().authState.loginUser.userId);
            jwtAxios.post(globals.urls.company + "addCoupon", coupon).then((response) => {
                notify.success("Coupon was added!");
                history.push("/Company/home");
            }).catch(error =>{
                notify.error(error.response.data);
            })
        }
    }

    return (
        <div className="AddCoupon addCoupon">
			<h2>Add new Coupon to the system<hr/></h2>
            <form onSubmit={handleSubmit(send)}>
                    <h3>Category</h3>
                    <Select style={{width:300}} {...register("category",{
                        required: {value:true, message : "Required field"}
                    })}>
                        <MenuItem value={"FOOD"}>Food</MenuItem>
                        <MenuItem value={"FASHION"}>Fashion</MenuItem>
                        <MenuItem value={"ELECTRICITY"}>Electricity</MenuItem>
                        <MenuItem value={"VACATION"}>Vacation</MenuItem>
                    </Select>
                    <br/>
                    <span> {errors.category && <p>{errors.category.message}</p>}</span>
                    <br/>
                    <TitleRounded style={{fontSize:40, margin :10}}/>
                    <TextField label="Title" variant="outlined" helperText="Required"
                         {...register("title", {
                            required: {value:true, message : "Required field"}
                         })}/>
                    <br/>
                    <span> {errors.title && <p>{errors.title.message}</p>}</span>
                    <br/>
                    <DescriptionOutlined style={{fontSize:40, margin :10}}/>
                    <TextField label="Description" variant="outlined" helperText="Required"
                        {...register("description",{
                            required: {value:true, message : "Required field"},
                            maxLength : {value : 250, message : "max length is 250"}
                    })}/>
                    <br/>
                    <span> {errors.description && <p>{errors.description.message}</p>}</span>
                    <br/>
                    <LocalOffer style={{fontSize:40, margin :10}}/>
                    <TextField type="number" label="Price" variant="outlined" helperText="Required"
                        {...register("price",{
                            required: {value:true, message : "Required field"},
                            min : {value : 1 , message :"minimum price is 1 NIS"}
                    })}/>
                    <br/>
                    <span> {errors.price && <p>{errors.price.message}</p>}</span>
                    <br/>
                    <Add style={{fontSize:40, margin :10}}/>
                    <TextField type="number" label="Quantity" variant="outlined" helperText="Required"
                        {...register("amount",{
                            required: {value:true, message : "Required field"},
                            min : {value : 1, message :"minimum QTY is 1 Piece"}
                    })}/>
                    <br/>
                    <span> {errors.amount && <p>{errors.amount.message}</p>}</span>
                    <br/>
                    <Image style={{fontSize:40, margin :10}}/>
                    <TextField label="Image" variant="outlined" helperText="Required"
                         {...register("image", {
                            required: {value:true, message : "Required field"}
                         })}/>
                    <br/>
                    <span> {errors.image && <p>{errors.image.message}</p>}</span>
                    <br/>
                    <TextField 
                        {...register("startDate", {required: {value:true, message : "Required field"}})}
                        id="datetime-local"
                        label="Coupon Start Date"
                        type="date"
                        helperText="Required"
                        className={classes.textField}
                        InputLabelProps={{
                        shrink: true,
                        }}
                    />
                    <br/><br/>
                    <TextField 
                        {...register("endDate", {required: {value:true, message : "Required field"}})}
                        id="datetime-local"
                        label="Coupon Expiration Date"
                        type="date"
                        helperText="Required"
                        className={classes.textField}
                        InputLabelProps={{
                        shrink: true,
                        }}
                    />
                    <br/><br/>
                    <ButtonGroup variant="contained" fullWidth>
                        <Button type="submit" color="secondary">Add</Button>
                    </ButtonGroup>
                </form>
        </div>
    );
}

export default AddCoupon;
