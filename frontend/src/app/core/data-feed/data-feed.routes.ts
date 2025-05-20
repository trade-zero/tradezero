import { Routes } from '@angular/router';
import { DataFeedListComponent } from "@core/data-feed/data-feed-list/data-feed-list.component";
import { DataFeedFormComponent } from "@core/data-feed/data-feed-form/data-feed-form.component";

export default [
    { path: 'list', component: DataFeedListComponent },
    { path: 'form', component: DataFeedFormComponent },
    { path: '**', redirectTo: '/notfound' }
] as Routes;
