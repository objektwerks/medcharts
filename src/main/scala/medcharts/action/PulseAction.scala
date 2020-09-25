package medcharts.action

import java.awt.event.ActionEvent
import java.util.concurrent.atomic.AtomicInteger

import javax.swing.AbstractAction
import medcharts.Conf
import medcharts.chart.PulseChart
import medcharts.entity._
import medcharts.entity.Transformer._
import medcharts.panel.ChartPanelBuilder
import medcharts.ui.{Frame, PathDialog}

class PulseAction(name: String, frame: Frame) extends AbstractAction(name) {
  protected val counter = new AtomicInteger(1)

  def actionPerformed(event: ActionEvent): Unit = {
    val path = new PathDialog(frame, Conf.labelPulseCsv).view
    path match {
      case Some( pulseCsvPath ) =>
        val pulses = transformEntities[Pulse](pulseCsvPath)
        val chart = PulseChart.build(pulses)
        val chartPanel = ChartPanelBuilder.build(chart, pulses)
        frame.addCompositeChartPanel(s"${Conf.titlePulse}-${counter.getAndIncrement}", chartPanel)
      case None =>
    }
  }
}