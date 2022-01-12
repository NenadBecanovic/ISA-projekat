import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminAnswer } from 'src/app/model/admin-answer';
import { ReportAppealAnswer } from 'src/app/model/report-appeal-answer';
import { ReportService } from 'src/app/service/report.service';

@Component({
  selector: 'app-report-answer-dialog',
  templateUrl: './report-answer-dialog.component.html',
  styleUrls: ['./report-answer-dialog.component.css']
})
export class ReportAnswerDialogComponent implements OnInit {

  reportId: number = 0;
  answer: ReportAppealAnswer = new ReportAppealAnswer();

  constructor(public dialogRef: MatDialogRef<ReportAnswerDialogComponent>, private _reportService: ReportService) { }

  ngOnInit() {
  }

  sendAnswer(){
    this._reportService.sendReportResponse(this.reportId, this.answer).subscribe(
      (ok: Boolean) => {
        this.dialogRef.close();
      },
      (error) => {
        // console.log(error)
      }
    )
  }

}
