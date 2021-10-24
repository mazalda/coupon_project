import Coupon from "../../models/Coupon";
import SingleCoupon from "../SingleCoupon/SingleCoupon";
import "./SingleCompany.css";

interface SingleCompanyProps{
    id:number,
    name:string,
    email:string,
    password:string,
    coupons:Coupon[]
}

function SingleCompany(props:SingleCompanyProps): JSX.Element {
    return (
        <div className="SingleCompany companySmallBox">
            Company id: {props.id}<hr/>
            Company name: {props.name}<br/>
            Company email: {props.email}<br/>
            Company password: {props.password}<br/><br/>
            <b>{props.coupons.length ===0 && <> This company has no coupons yet </>}</b><br/>
            <b>{props.coupons.length>0 && <> Company's Coupons: </>}</b><br/>
            {props.coupons.map(item => <SingleCoupon
                key={item.id}
                id={item.id}
                companyId={item.companyId}
                category={item.category}
                title={item.title}
                description={item.description}
                startDate={item.startDate}
                endDate={item.endDate}
                amount={item.amount}
                price={item.amount}
                image={item.image}/>)}
        </div>
    );
}

export default SingleCompany;
