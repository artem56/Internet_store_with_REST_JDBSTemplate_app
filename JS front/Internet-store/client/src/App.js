import { BrowserRouter } from "react-router-dom";
import AppRouter from "./components/AppRouter";
import { observer } from 'mobx-react-lite';
import NavBar from "./components/NavBar";
import { useContext, useEffect, useState } from "react";
import { Context } from "./index";
import { check } from "./http/user_API";

const App = observer(() => {
    const { user } = useContext(Context);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        check().then(data => {
            user.setUser(data); // устанавливаем данные пользователя
            user.setIsAuth(true);
        }).finally(() => setLoading(false));
    }, [user]);

    if (loading) {
        return (
            <div className="d-flex align-items-center">
                <strong>Loading...</strong>
                <div className="spinner-border ml-auto" role="status" aria-hidden="true"></div>
            </div>
        );
    }

    return (
        <BrowserRouter>
            <NavBar />
            <AppRouter />
        </BrowserRouter>
    );
});

export default App;
