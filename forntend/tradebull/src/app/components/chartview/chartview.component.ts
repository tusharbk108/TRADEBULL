import { Component, OnInit ,ViewEncapsulation} from '@angular/core';
import { chartData } from 'src/app/datasource'; 
@Component({
  selector: 'app-chartview',
  templateUrl: './chartview.component.html',
  styleUrls: ['./chartview.component.css']
})
export class ChartviewComponent implements OnInit {
  public stockchartData: Object[];
  public xAxis:Object;
  constructor() { this.xAxis={
    valueType:'DateTime'
   }; }
 
  public title: string;
ngOnInit(): void {
    // Title for chart
    this.title = 'Stock Price';
    this.stockchartData = chartData;
}

}