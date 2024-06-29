using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace ConsoleApplication3
{
    class Program
    {
        static void Main(string[] args)
        {
            var files = new[] { "D:\\RnD\\os\\src\\liquidbase\\liquibase2.xml", @"D:\RnD\os\src\uemr\esm\openmrs-module-labmanagement\api\src\main\resources\liquibase.xml" };
            foreach (var file in files) {
                try
                {
                    var xmlDocument = new XmlDocument();
                    xmlDocument.Load(file);
                    var ns = new XmlNamespaceManager(xmlDocument.NameTable);
                    ns.AddNamespace("ns", "http://www.liquibase.org/xml/ns/dbchangelog/1.9");
                    var i = 1;
                    foreach (XmlNode xmlNode in xmlDocument.DocumentElement.SelectNodes("//ns:changeSet", ns))
                    {
                        xmlNode.Attributes["id"].Value = $"labmanagement-1719626186290-{i++}";
                    }

                    xmlDocument.Save(file);
                    Console.WriteLine($"Successfully formated file {file}");
                }
                catch (Exception exception)
                {
                    Console.WriteLine($"Failed to format file {file}:{Environment.NewLine}{exception.ToString()}");
                }
            }
        }
    }
}
