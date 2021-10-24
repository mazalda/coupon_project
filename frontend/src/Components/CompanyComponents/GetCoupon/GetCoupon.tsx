import { useEffect, useState } from "react";
import { useHistory } from "react-router";
import Coupon from "../../models/Coupon";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import Button from '@material-ui/core/Button';
import { ArrowBackIosOutlined, Delete, Send} from "@material-ui/icons";
import "./GetCoupon.css";

interface GetCouponProps{
    id:string
}

function GetCoupon(props: GetCouponProps): JSX.Element {
    const [couponData, setData] = useState(new Coupon());
    let history = useHistory();

    useEffect(() => {
        jwtAxios.post(globals.urls.company +"getOneCoupon/" + `${props.id}`).then((response) => {
            console.log(response.data);
            setData(response.data);
        }).catch (error => {
            notify.error(error.response.data);
        })
    },[])

    function updateCoupon(){
        history.push("/Company/updateCoupon/" + `${couponData.id}`)
    }

    function deleteCoupon(){
        jwtAxios.delete(globals.urls.company + "deleteCoupon/" + `${couponData.id}`)
        .then(()=>{
            notify.success("Coupon was deleted succesfully");
            history.push("/Company/home");
        }).catch(error => {
            notify.error(error.response.data);
        })
    }

    function backToHome(){
        history.push("/Company/home");
    }

    return (
        <div className="GetCoupon">
			<br/>
            Full Coupon Details for coupon id: {props.id}<hr/>
            <div className="smallBox">
                Category: {couponData.category}<hr/>
                Title: {couponData.title}<br/>
                Description: {couponData.description}<br/>
                Start Date: {couponData.startDate}<br/>
                End Date: {couponData.endDate}<br/>
                Amount: {couponData.amount}<br/>
                Cost: {couponData.price}₪<br/><br/>
                <img src={couponData.image} width="250" height="250"/><br/><br/>
                <Button size="small" variant="contained" endIcon={<Send/>} color="secondary" onClick={updateCoupon}>
                Update
                </Button>
                &nbsp;&nbsp;
                <Button size="small" variant="outlined" startIcon={<Delete/>} color="secondary" onClick={deleteCoupon}>
                Delete
                </Button>
                &nbsp;&nbsp;
                <Button size="small" variant="outlined" startIcon={<ArrowBackIosOutlined/>} color="secondary" onClick={backToHome}>
                Back
                </Button>
            </div>
        </div>
    );
}

export default GetCoupon;
