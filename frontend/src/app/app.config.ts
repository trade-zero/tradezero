import { provideHttpClient, withFetch } from '@angular/common/http';
import { APP_INITIALIZER , ApplicationConfig , provideZoneChangeDetection } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter, withEnabledBlockingInitialNavigation, withInMemoryScrolling } from '@angular/router';
import Aura from '@primeng/themes/aura';
import { providePrimeNG } from 'primeng/config';
import { appRoutes } from './app.routes';
import { ConfigService } from '@shared/config/config.service';
import { environment } from '../environments/environment';

export function initConfig(configService: ConfigService): () => Promise<any> {
    return (): Promise<any> => {
        return new Promise(
            (resolve, reject) => {
                configService.load(environment.CONFIG_FILE).then((res) => resolve({})).catch(() => reject());
            });
    };
}


export const appConfig: ApplicationConfig = {
    providers: [
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideRouter(appRoutes),
        provideRouter(
            appRoutes,
            withInMemoryScrolling({
                anchorScrolling: 'enabled',
                scrollPositionRestoration: 'enabled'
            }),
            withEnabledBlockingInitialNavigation()
        ),
        provideHttpClient(withFetch()),
        provideAnimationsAsync(),
        providePrimeNG({ theme: { preset: Aura, options: { darkModeSelector: '.app-dark' } } }),
        {
            provide: APP_INITIALIZER,
            useFactory: (configService: ConfigService) => initConfig(configService),
            deps: [ConfigService],
            multi: true,
        },
    ]
};
