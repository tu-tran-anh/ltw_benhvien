import React, { useState } from "react";
import API from "../callAPI";

const MainContext = React.createContext();

const MainApp = ({ children }) => {
    const [isLogin, setIsLogin] = useState(false);

    React.useEffect(async () => {
    }, [])
    return (
        <MainContext.Provider
            value={{
                isLogin,
                setIsLogin,
                async getCurrentUser() {
                    return null
                },
            }}
        >
            {children}
        </MainContext.Provider>
    )
}

export {
    MainContext,
    MainApp
}