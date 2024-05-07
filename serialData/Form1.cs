using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.IO.Ports;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace serialData
{
    public partial class UYDU : Form
    {
        public static SerialPort port;
        public static string File_path = @"C:\Users\aysei\OneDrive\Masaüstü\CS\8th semester\bitirme\uyduTelemetri.csv";
        int period;
        string[] data;
        bool timerWork= false;
        public UYDU()
        {
            InitializeComponent();
        }


        private void Form1_Load(object sender, EventArgs e)
        {
            comports.Items.Clear();
            String[] ports = SerialPort.GetPortNames();
            comports.Items.AddRange(ports);
            data = File.ReadAllText(File_path).Split('\n');

        }
        int i = 0;  
        private void timer1_Tick(object sender, EventArgs e)
        {
            serialPort1.WriteLine(data[i]);
            textBox2.Text += data[i].ToString();
            i++;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                if (comports.Text == "")
                {
                    MessageBox.Show("Error!: Choose COM Port and Baud Rate!");
                }
                else
                {
                    serialPort1.PortName = comports.Text;
                    serialPort1.Parity = Parity.None;
                    serialPort1.StopBits = StopBits.One;
                    serialPort1.Open();
                    port = serialPort1;

                }
            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }

        private void serialPort1_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            try
            {
                this.Invoke((MethodInvoker)delegate
                {
                    String newData = serialPort1.ReadExisting();

                    textBox1.Text += newData;
                    String[] command = newData.Split(',');
                    if (command[0] == "telemetry")
                    {
                        period = int.Parse(command[1]);
                        if (timerWork)
                        {
                            timer1.Stop();
                        }
                        timer1.Interval = period;
                        timer1.Start();
                        timerWork = true;
                    }
                });

            }
            catch (Exception err)
            {
                MessageBox.Show(err.Message);
            }
        }


    }
}
