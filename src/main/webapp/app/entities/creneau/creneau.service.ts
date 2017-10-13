import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Creneau } from './creneau.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CreneauService {

    private resourceUrl = SERVER_API_URL + 'api/creneaus';

    constructor(private http: Http) { }

    create(creneau: Creneau): Observable<Creneau> {
        const copy = this.convert(creneau);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(creneau: Creneau): Observable<Creneau> {
        const copy = this.convert(creneau);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Creneau> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Creneau.
     */
    private convertItemFromServer(json: any): Creneau {
        const entity: Creneau = Object.assign(new Creneau(), json);
        return entity;
    }

    /**
     * Convert a Creneau to a JSON which can be sent to the server.
     */
    private convert(creneau: Creneau): Creneau {
        const copy: Creneau = Object.assign({}, creneau);
        return copy;
    }
}
