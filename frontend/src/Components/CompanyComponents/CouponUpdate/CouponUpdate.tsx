import { useEffect, useState } from "react";
import { Button, ButtonGroup, createStyles, makeStyles, MenuItem, Select, TextField, Theme } from "@material-ui/core";
import { useHistory } from "react-router-dom";
import Coupon from "../../models/Coupon";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import "./CouponUpdate.css";
import { useForm } from "react-hook-form";
import store from "../../Redux/store";
import notify from "../../Util/Notify";
import { Cancel, Send } from "@material-ui/icons";

interface CouponUpdateProps{
    id:string;
}

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

function CouponUpdate(props: CouponUpdateProps): JSX.Element {
    const classes = useStyles();
    let history = useHistory();
    const {handleSubmit} = useForm<Coupon>();
    const [id, setId] = useState(0);
    const [companyId, setCompanyId] = useState(0); 
    const [category, setCategory] = useState("");
    const [title, setTitle] = useState("");
    const [desc, setDesc] = useState("");
    const [img, setImg] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [amount, setAmount] = useState(0);
    const [price, setPrice] = useState(0);

    useEffect(()=>{
        jwtAxios.post(globals.urls.company+"getOneCoupon/" + `${props.id}`).then((response) => {
            console.log(response.data);
            var coupon:Coupon = new Coupon();
            coupon = response.data;

            setId(coupon.id);
            setCompanyId(coupon.companyId);
            setCategory(coupon.category);
            setTitle(coupon.title);
            setDesc(coupon.description);
            setImg(coupon.image);
            setStartDate(coupon.startDate.toString());
            setEndDate(coupon.endDate.toString());
            setAmount(coupon.amount);
            setPrice(coupon.price);
        })
    },[])
    
    function update(){
        if(store.getState().authState.loginUser.clientType !== "COMPANY"){
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            var updatedCoupon = new Coupon();
            updatedCoupon.id = id;
            updatedCoupon.companyId = companyId;
            updatedCoupon.category = category;
            updatedCoupon.title = title;
            updatedCoupon.description = desc;
            updatedCoupon.startDate = new Date(startDate);
            updatedCoupon.endDate = new Date (endDate);
            updatedCoupon.amount = amount;
            updatedCoupon.price = price;
            updatedCoupon.image = img;


            jwtAxios.post(globals.urls.company + "updateCoupon", updatedCoupon).then((response) => {
                notify.success("Coupon was updated!");
                history.push("/Company/home")
            }).catch(error =>{
                notify.error(error.response.data);
            })
        }
    }

    function cancel(){
        history.push("/Company/home")
    }
    
    return (
        <div className="CouponUpdate smallBox">
			<h3>Update Coupon id: {props.id}</h3><hr/>
            <form onSubmit={handleSubmit(update)}>
                    <br/>
                    Category<br/>
                    <Select style={{width:250}} value={category} onChange={e => setCategory(e.target.value.toString())}>
                        <MenuItem value={"FOOD"}>Food</MenuItem>
                        <MenuItem value={"FASHION"}>Fashion</MenuItem>
                        <MenuItem value={"ELECTRICITY"}>Electricity</MenuItem>
                        <MenuItem value={"VACATION"}>Vacation</MenuItem>
                    </Select>
                    <br/>
                    <br/><br/>
                    <TextField label="Title" variant="outlined" value={title} onChange={e => setTitle(e.target.value)}/>
                    <br/>
                    <br/>
                    <TextField label="Description" variant="outlined" value={desc} onChange={e => setDesc(e.target.value)}/>
                    <br/>
                    <br/>
                    <TextField type="number" label="Price" variant="outlined" value={price} onChange={e => setPrice(parseInt(e.target.value))}/>
                    <br/>
                    <br/>
                    <TextField type="number" label="Quantity" variant="outlined" value={amount} onChange={e => setAmount(parseInt(e.target.value))}/>
                    <br/>
                    <br/>
                    <TextField 
                        id="datetime-local"
                        label="Coupon Start Date"
                        type="date"
                        value={startDate}
                        onChange={e => setStartDate(e.target.value)}
                        className={classes.textField}
                        InputLabelProps={{
                        shrink: true,
                        }}
                    />
                    <br/><br/>
                    <TextField 
                        id="datetime-local"
                        label="Coupon Expiration Date"
                        type="date"
                        value={endDate}
                        onChange={e => setEndDate(e.target.value)}
                        className={classes.textField}
                        InputLabelProps={{
                        shrink: true,
                        }}
                    />
                    <br/><br/>
                    <ButtonGroup variant="contained" fullWidth>
                        <Button type="submit" size="small" endIcon={<Send/>} color="secondary">Updated</Button>
                    </ButtonGroup>
                    <br/><br/>
                    <ButtonGroup variant="outlined" fullWidth>
                        <Button size="small" startIcon={<Cancel/>} color="secondary" onClick={cancel}>Cancel</Button>
                    </ButtonGroup>
                    <br/>
                </form>
        </div>
    );
}

export default CouponUpdate;
