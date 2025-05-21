import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { LoadingService } from '@shared/services/loading.service';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { AsyncPipe, NgIf } from '@angular/common';

@Component({
    selector: 'app-loading',
    templateUrl: './loading.component.html',
    styleUrl: './loading.component.scss',
    standalone: true,
    imports: [ProgressSpinnerModule, AsyncPipe, NgIf]
})
export class LoadingComponent {
    isLoading$: Observable<boolean>;

    constructor(private loadingService: LoadingService) {
        this.isLoading$ = this.loadingService.isLoading$;
    }
}
