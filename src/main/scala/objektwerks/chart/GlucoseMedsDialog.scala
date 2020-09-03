package objektwerks.chart

import java.awt.BorderLayout

import java.awt.event.{ActionEvent, ActionListener}

import javax.swing.{JButton, JDialog, JFileChooser, JLabel, JPanel}
import javax.swing.filechooser.FileSystemView
import javax.swing.filechooser.FileNameExtensionFilter

import net.miginfocom.swing.MigLayout

class GlucoseMedsDialog(frame: Frame) extends JDialog {
  private var pathToGlucoseCsv = Option.empty[String]
  private var pathToMedsCsv = Option.empty[String]

  def view(): (Option[String], Option[String]) = {
    setTitle(Conf.glucoseMedsDialogTitle)
    add(buildSelectPanel(Conf.glucoseMedsSelectLabel, Conf.glucoseMedsCancelLabel), BorderLayout.CENTER)
    setModal(true)
    setLocationRelativeTo(frame)
    pack()
    setVisible(true)
    (pathToGlucoseCsv, pathToMedsCsv)
  }

  private def buildSelectPanel(selectButtonLabel: String, cancelButtonLabel: String): JPanel = {
    val panel = new JPanel(new MigLayout())
    panel.add( new JLabel(Conf.glucoseCsvLabel) )
    panel.add( buildGlucoseSelectButton(selectButtonLabel) )
    panel.add( new JLabel(Conf.medsCsvLabel) )
    panel.add( buildMedsSelectButton(selectButtonLabel) )
    panel.add( buildCancelButton(cancelButtonLabel) )
    panel.add( buildSelectButton(selectButtonLabel) )
    panel
  }

  private def buildGlucoseSelectButton(label: String): JButton = {
    val button = new JButton(label)
    button.addActionListener( new ActionListener() {
      override def actionPerformed(event: ActionEvent): Unit = pathToGlucoseCsv = selectFile
    })
    button
  }

  private def buildMedsSelectButton(label: String): JButton = {
    val button = new JButton(label)
    button.addActionListener( new ActionListener() {
      override def actionPerformed(event: ActionEvent): Unit = pathToMedsCsv = selectFile
    })
    button
  }

  private def buildCancelButton(label: String): JButton = {
    val button = new JButton(label)
    button.addActionListener( new ActionListener() {
      override def actionPerformed(event: ActionEvent): Unit = setVisible(false)
    })
    button
  }

  private def buildSelectButton(label: String): JButton = {
    val button = new JButton(label)
    button.addActionListener( new ActionListener() {
      override def actionPerformed(event: ActionEvent): Unit = setVisible(false)
    })
    button
  }

  private def selectFile: Option[String] = {
    val fileChooser = new JFileChooser(FileSystemView.getFileSystemView.getHomeDirectory)
    fileChooser.setDialogTitle(Conf.glucoseMedsFileChooserTitle)
    fileChooser.setAcceptAllFileFilterUsed(false)
    val filter = new FileNameExtensionFilter(Conf.glucoseMedsFileExtensionFilter, "csv", "txt")
    fileChooser.addChoosableFileFilter(filter)
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
      Some(fileChooser.getSelectedFile.getAbsolutePath)
    else None
  }
}