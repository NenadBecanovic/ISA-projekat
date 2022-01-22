import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Feedback } from '../model/feedback';
import { FeedbackInfo } from '../model/feedback-info';
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class FeedbackService {

    constructor(private _http:HttpClient) { }

    private readonly userPath = environment.backend_api + 'api/feedback';

    public saveFeedback(feedback: Feedback): Observable<Feedback>{
        return this._http.post<Feedback>(`${this.userPath}/saveFeedback`,feedback)
    }

    public getAll(): Observable<FeedbackInfo[]>{
        return this._http.get<FeedbackInfo[]>(`${this.userPath}/getAllFeedbacks`);
    }

    public delete(id: number): Observable <boolean> {
        return this._http.delete<boolean>(`${this.userPath}/delete/` + id)
    }

    public approveFeedback(feedback: FeedbackInfo): Observable <boolean> {
        return this._http.put<boolean>(`${this.userPath}/approve/`+feedback.id, feedback);
    }

    public getAllFeedbacksByAdventureId(id: number): Observable<FeedbackInfo[]>{
        return this._http.get<FeedbackInfo[]>(`${this.userPath}/getAllFeedbacksByAdventure/`+id);
    }

    public getAllFeedbacksByInstructorId(id: number): Observable<FeedbackInfo[]>{
        return this._http.get<FeedbackInfo[]>(`${this.userPath}/getAllFeedbacksByInstructor/`+id);
    }
}
