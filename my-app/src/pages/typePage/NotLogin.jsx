import React from "react"
import {MainContext} from "../../context"
import { useHistory } from "react-router-dom";

export default function NotLogin({children}){
    let history = useHistory();
    const {isLogin} = React.useContext(MainContext);
    console.log("vao trong cai not login");   
    React.useEffect(()=>{
        if(isLogin){
            console.log("chay ve trang home ",isLogin);
            history.push("/");
        }
    })
    return (
        <div>
            {children}
        </div>
    )
}