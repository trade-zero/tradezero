import { HttpClient } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn : 'root'
})
export class ConfigService {
    config: any;
    injectHttpB: HttpClient;

    constructor(
        private injector: Injector,
    ) {
        this.injectHttpB = this.injector.get(HttpClient);
    }

    load(url: string) {

        const injectHttp = this.injector.get(HttpClient);

        return new Promise(
            (resolve) => {
                this.injectHttpB.get(url).pipe(map(res => res)).subscribe(
                    (config) => {
                        this.config = config;
                        resolve({});
                    });
            });
    }

    /**
     * @param {string} [element]
     * @param {string} [dataList]
     * @returns 'Data Lista is Index Array'
     * @memberof ConfigService
     */
    getUrl(element: string, dataList?: string) {
        if (this.config) {
            if (!dataList) {
                const urlWithElement = this.config['API'][element];
                return this.normalizeUrl(urlWithElement);
            } else {
                const urlWithDataList = this.config['API'][dataList][element];
                return this.normalizeUrl(urlWithDataList);
            }
        } else {
            return null;
        }
    }

    /**
     * @param typeModel
     * @returns {any}
     */
    verifyUrl(typeModel: any): string {

        if (typeModel) {
            if (typeModel.includes('/', typeModel.length - 1)) {
                return typeModel;
            } else {
                return `${ typeModel }/`;
            }
        }
        return '/';
    }

    /**
     * Verifica e normaliza uma URL, garantindo que termine com uma barra.
     *
     * @param url A URL a ser verificada.
     * @returns A URL normalizada, terminando com uma barra, ou uma barra simples se a URL for nula, indefinida ou vazia.
     */
    normalizeUrl(url: any): string {
        if (url) {
            if (url.includes('/', url.length - 1)) {
                return url;
            } else {
                return `${url}/`;
            }
        }
        return '/';
    }

    getEndPoint(elementUrl: string,
                elementPath: string) {
        if (this.config) {
            const url = this.normalizeUrl(this.config['API'][elementUrl]);
            const path = this.normalizeUrl(this.config['CONTROLLER'][elementPath]);
            return url + path;
        } else {
            return '';
        }
    }

    getEnvironments(element: string,
                    dataList?: string) {
        if (!dataList) {
            return this.config[element];
        } else {
            return this.config[dataList][element];
        }
    }
}
