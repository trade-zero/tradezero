import { Routes } from '@angular/router';
import { AppLayout } from "./layout/component/app.layout";
import { Landing } from "./pages/landing/landing";
import { Notfound } from "./pages/notfound/notfound";

export const appRoutes: Routes = [
    {
        path: '',
        component: AppLayout,
        children: [
            { path: 'data-feed', loadChildren: () => import('@core/data-feed/data-feed.routes') },
            { path: 'portifolio', loadChildren: () => import('@core/portfolio/portfolio.routes') },
            { path: 'ristk-management', loadChildren: () => import('@core/risk-management/risk-management.routes') },
            { path: 'trade-zero', loadChildren: () => import('@core/trade-zero/trade-zero.routes') },
        ]
    },
    { path: 'landing', component: Landing },
    { path: 'notfound', component: Notfound },
    { path: 'auth', loadChildren: () => import('./pages/auth/auth.routes') },
    { path: '**', redirectTo: '/notfound' }
];
