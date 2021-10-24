import Button from '@material-ui/core/Button';
import { useHistory } from "react-router";
import store from "../../Redux/store";
import "./SingleCoupon.css";

interface SingleCouponProps{
     id:number,
     companyId:number,
     category:string,
     title:string,
     description:string,
     startDate:Date,
     endDate:Date,
     amount:number,
     price:number,
     image:string;
}

function SingleCoupon(props:SingleCouponProps): JSX.Element {
    let history = useHistory();
    
    function moreInfo(){
        let clientType = store.getState().authState.loginUser.clientType;
        if(clientType === "ADMINISTRATOR"){
            history.push("/Admin/fullCouponDetails/" + `${props.id}`);
        } else if(clientType === "COMPANY"){
            history.push("/Company/getCoupon/" + `${props.id}`);
        } else if(clientType === "CUSTOMER"){
            history.push("/Customer/getCoupon/" + `${props.id}`);
        } else {
            history.push("/home/couponDetailsForGuests/" + `${props.id}`);
        }
    }
    
    return (
        <div className="SingleCoupon couponSmallBox">
			{props.category}<hr/>
            Title: {props.title}<br/>
            Description: {props.description}<br/>
            <img src={props.image} width="100" height="100"/><br/>
            <Button size="small" variant="contained" color="secondary" onClick={moreInfo}>
                More Info
            </Button>
        </div>
    );
}

export default SingleCoupon;
