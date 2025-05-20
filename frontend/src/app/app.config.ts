import { provideHttpClient , withFetch } from '@angular/common/http';
import { ApplicationConfig , provideZoneChangeDetection } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter , withEnabledBlockingInitialNavigation , withInMemoryScrolling } from '@angular/router';
import Aura from '@primeng/themes/aura';
import { providePrimeNG } from 'primeng/config';
import { appRoutes } from './app.routes';

export const appConfig: ApplicationConfig = {

    providers: [
        provideZoneChangeDetection({eventCoalescing: true}) , provideRouter(appRoutes) ,
        provideRouter(appRoutes , withInMemoryScrolling({
            anchorScrolling: 'enabled' ,
            scrollPositionRestoration: 'enabled'
        }) , withEnabledBlockingInitialNavigation()) ,
        provideHttpClient(withFetch()) ,
        provideAnimationsAsync() ,
        providePrimeNG({theme: {preset: Aura , options: {darkModeSelector: '.app-dark'}}})
    ]
};
