import Coupon from "../../models/Coupon";
import SingleCoupon from "../SingleCoupon/SingleCoupon";
import "./SingleCustomer.css";

interface SingleCustomerProps{
    id:number,
    firstName:string,
    lastName:string,
    email:string,
    password:string,
    coupons:Coupon[]
}

function SingleCustomer(props:SingleCustomerProps): JSX.Element {
    return (
        <div className="SingleCustomer customerSmallBox">
			Customer id: {props.id}<hr/>
            Customer first name: {props.firstName}<br/>
            Customer last name: {props.lastName}<br/>
            Customer email: {props.email}<br/>
            Customer password: {props.password}<br/><br/>
            <b>{props.coupons.length ===0 && <> This customer has no coupons yet </>}</b><br/>
            <b>{props.coupons.length>0 && <> Customer's Coupons: </>}</b><br/>
            {props.coupons.map(item => <SingleCoupon
                key={item.id}
                id={item.id}
                companyId={item.companyId}
                category={item.category}
                title={item.title}
                description={item.description}
                startDate={item.startDate}
                endDate={item.endDate}
                price={item.price}
                amount={item.amount}
                image={item.image}
                />)}
        </div>
    );
}

export default SingleCustomer;
