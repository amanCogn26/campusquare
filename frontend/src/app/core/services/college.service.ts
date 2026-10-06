import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CollegeResponseDto } from '../models/college/college-response-dto';
import { environment } from '../../../environment/environment.development';
import { CollegeRequestDto } from '../models/college/college-request-dto';

@Injectable({
  providedIn: 'root',
})
export class CollegeService {

  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}`;

  getAllColleges(): Observable<CollegeResponseDto[]> {
    return this.http.get<CollegeResponseDto[]>(`${this.apiUrl}/colleges`, {});
  }

  createCollege(collegeRequestDto:CollegeRequestDto): Observable<CollegeResponseDto> {
    return this.http.post<CollegeResponseDto>(`${this.apiUrl}/colleges/create`, collegeRequestDto);
  }

  getCollege(collegeName:string): Observable<CollegeResponseDto> {
    return this.http.get<CollegeResponseDto>(`${this.apiUrl}/colleges/${collegeName}`, {});
  }

  deleteCollege(collegeId:number):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/colleges/${collegeId}`,{});
  }
}
