package medcharts.chart

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Dimension}

import javax.swing._
import javax.swing.filechooser.{FileNameExtensionFilter, FileSystemView}

import medcharts.Conf
import medcharts.ui.Frame

import net.miginfocom.swing.MigLayout

class GlucoseMedsDialog(frame: Frame) extends JDialog {
  private val glucoseCsvTextField = buildTextField
  private val medsCsvTextField = buildTextField
  private val selectButton = buildSelectButton(Conf.selectLabel)

  def view(): (String, String) = {
    setTitle(Conf.glucoseMedsDialogTitle)
    add(buildSelectPanel(Conf.cancelLabel, Conf.ellipsisLabel), BorderLayout.CENTER)
    setModal(true)
    pack()
    setLocationRelativeTo(frame)
    setVisible(true)
    (glucoseCsvTextField.getText, medsCsvTextField.getText)
  }

  private def buildSelectPanel(cancelLabel: String, ellipsisLabel: String): JPanel = {
    val panel = new JPanel( new MigLayout() )
    panel.add( new JLabel(Conf.glucoseCsvLabel), "align label" )
    panel.add( glucoseCsvTextField, "grow" )
    panel.add( buildGlucoseSelectButton(ellipsisLabel), "wrap" )
    panel.add( new JLabel(Conf.medsCsvLabel), "align label" )
    panel.add( medsCsvTextField, "grow" )
    panel.add( buildMedsSelectButton(ellipsisLabel), "wrap" )
    panel.add( buildCancelButton(cancelLabel), "span, split 2, align right" )
    panel.add( selectButton )
    panel
  }

  private def buildTextField: JTextField = {
    val textField = new JTextField()
    textField.setEditable(false)
    textField.setPreferredSize(new Dimension(400, 30))
    textField
  }

  private def buildGlucoseSelectButton(ellipsisLabel: String): JButton = {
    val button = new JButton(ellipsisLabel)
    button.addActionListener( new ActionListener() {
      override def actionPerformed(event: ActionEvent): Unit = {
        glucoseCsvTextField.setText( selectFile.getOrElse("") )
        validateCsvTextFields()
      }
    })
    button
  }

  private def buildMedsSelectButton(ellipsisLabel: String): JButton = {
    val button = new JButton(ellipsisLabel)
    button.addActionListener( new ActionListener() {
      override def actionPerformed(event: ActionEvent): Unit = {
        medsCsvTextField.setText( selectFile.getOrElse("") )
        validateCsvTextFields()
      }
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
    button.setEnabled(false)
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
    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
      Some(fileChooser.getSelectedFile.getAbsolutePath)
    else None
  }

  private def validateCsvTextFields(): Unit =
    if (glucoseCsvTextField.getText.nonEmpty && medsCsvTextField.getText.nonEmpty) selectButton.setEnabled(true)
}