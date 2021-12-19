import React from "react"
import {MainContext} from "../../context"
import { useHistory } from "react-router-dom";

export default function MustLogin({children}){
    let history = useHistory();
    const {isLogin} = React.useContext(MainContext);
    React.useEffect(()=>{
        if(!isLogin){
            history.push("/");
        }
    })
    return (
        <div>
            {children}
        </div>
    )
}