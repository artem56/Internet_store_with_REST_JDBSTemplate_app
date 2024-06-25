import Admin from "./pages/Admin";
import Auth from "./pages/Auth";
import Basket from "./pages/Cart";
import DevicePage from "./pages/DevicePage";
import Shop from "./pages/Shop";
import Orders from "./pages/Orders"; // Импортируем компонент Orders
import Profile from "./pages/Profile"; // Импортируем компонент Profile
import { ADMIN_ROUTE, BASKET_ROUTE, DEVICE_ROUTE, LOGIN_ROUTE, REGISTRATION_ROUTE, SHOP_ROUTE, ORDERS_ROUTE, PROFILE_ROUTE } from "./utils/consts";

export const authRoutes = [
    {
        path: ADMIN_ROUTE,
        Component: Admin
    },
    {
        path: BASKET_ROUTE,
        Component: Basket
    },
    {
        path: ORDERS_ROUTE,
        Component: Orders
    },
    {
        path: PROFILE_ROUTE,
        Component: Profile
    }
];

export const publicRoutes = [
    {
        path: SHOP_ROUTE,
        Component: Shop
    },
    {
        path: DEVICE_ROUTE + '/:id',
        Component: DevicePage
    },
    {
        path: LOGIN_ROUTE,
        Component: Auth
    },
    {
        path: REGISTRATION_ROUTE,
        Component: Auth
    }
];
