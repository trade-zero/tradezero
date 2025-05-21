import { Routes } from '@angular/router';
import { PortfolioListComponent } from "@core/portfolio/portfolio-list/portfolio-list.component";
import { PortfolioFormComponent } from "@core/portfolio/portfolio-form/portfolio-form.component";

export default [
    { path: '', redirectTo: 'list', pathMatch: 'full' },
    { path: 'list', component: PortfolioListComponent },
    { path: 'form', component: PortfolioFormComponent },
    { path: '**', redirectTo: '/notfound' }
] as Routes;
