import { BaseResourceModel } from '@shared/models/base-resource.model';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Inject, Injectable, Injector } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class BaseResourceService<T extends BaseResourceModel> {

    protected httpClient: HttpClient;

    constructor(
        @Inject('url') protected url: string,
        protected injector: Injector,
    ) {
        console.log('BaseService this.url =>', this.url);
        this.httpClient = injector.get(HttpClient);
    }

    getById(id: string | number): Observable<T> {

        return this.httpClient
            .get<T>(`${this.url}/${id}`)
            .pipe(catchError(this.handleError));

    }

    getAll(params?: { [param: string]: any }): Observable<T[]> {

        return this.httpClient
            .get<T[]>(`${this.url}`)
            .pipe(catchError(this.handleError));

    }

    create(resource: T): Observable<T> {

        return this.httpClient
            .post<T>(this.url, resource)
            .pipe(catchError(this.handleError));

    }

    update(resource: T, id?: any): Observable<T> {

        return this.httpClient
            .put<T>(`${this.url}/${resource.id}`, resource)
            .pipe(catchError(this.handleError));

    }

    delete(id: number | string): Observable<T> {

        return this.httpClient
            .delete<T>(`${this.url}${id}`)
            .pipe(catchError(this.handleError));

    }

    handleError(error: any) {

        console.error('Erro:', error);
        return throwError(() => new Error('Erro na requisição'));

    }
}
