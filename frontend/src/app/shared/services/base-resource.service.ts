import { BaseResourceModel } from '@shared/models/base-resource.model';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Inject, Injectable, Injector } from '@angular/core';
import { Location } from '@angular/common';
import { LoadingService } from '@shared/services/loading.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class BaseResourceService<T extends BaseResourceModel> {

  protected apiUrl: string = environment.apiUrl;
  protected httpClient: HttpClient;
  protected router: Router;
  protected location: Location;
  protected loadingService: LoadingService;

  constructor(
    @Inject('url') protected url: string,
    protected injector: Injector,
  ) {
    this.httpClient = injector.get(HttpClient);
    this.router = injector.get(Router);
    this.location = injector.get(Location);
    this.loadingService = injector.get(LoadingService);
    this.apiUrl = `${this.apiUrl}/${this.url}`;
  }

  getById(id: string | number): Observable<T> {

    return this.httpClient.get<T>(`${this.url}/${id}`).pipe(catchError(this.handleError));

  }

  getAll(params?: {[param: string]: any }): Observable<T[]> {

    return this.httpClient.get<T[]>(`${this.url}`).pipe(catchError(this.handleError));

  }

  create(resource: T): Observable<T> {
    return this.httpClient.post<T>(this.url, resource).pipe(catchError(this.handleError));
  }

  update(resource: T, id?: any): Observable<T> {
    return this.httpClient.put<T>(`${this.url}/${resource.id}`, resource).pipe(catchError(this.handleError));
  }

  delete(id: number | string): Observable<T> {

    return this.httpClient.delete<T>(`${this.url}/${id}`).pipe(catchError(this.handleError));

  }

  showLoading() {
    console.log('BaseResourceService showLoading');
    this.loadingService.show();
  }

  hideLoading() {
    console.log('BaseResourceService hideLoading');
    this.loadingService.hide();
  }

  handleError(error: any) {
    console.error('Erro:', error);
    return throwError(() => new Error('Erro na requisição'));
  }
}
