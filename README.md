# Software Requirements and Deployment team project

## Team Credentials

- Hugo Moslener 40241091
- Darcy McCoy 40234556
- Maelle Fieffe-Bedard 40231764 (team leader)

## Getting Started

### Setup the Database

1. Install MySQL server.
   - Dowload at this link: https://dev.mysql.com/downloads/mysql/
   - make sure to set the operating system to your computers OS and download whatever file you prefer
   - In the installer, keep all your server settings to default

    > [!WARNING]  
    > When setting up MySQL you will be prompted to create a password. Make note of it because you will need it later

1. Install MySQL Workbench
   - Download at this link: https://dev.mysql.com/downloads/workbench/
   - make sure the OS matches and download whatever file you prefer
   - Download the MSI installer and keep everything default in the installer

1. In MySQL Workbench
   - Connect to the local instance of the server you downloaded (will need to use password you created while installing server)
   - In the top bar options, select create a new schema in the connected server
   - Name this new schema: train_connections
   - hit apply both for both prompts
   - Next, on the left, there should be two tabs, select the schemas tab
   - Open the dropdown for the schema you just created and right click tables
   - Click on the table data import Wizard
   - Download the file linked in the wiki
   - in the wizard, select the file path to the file you just downloaded and click next
   - make sure the table is named eu_rail_network and click next
   - click next until the wizard is finished

1. back in the code, Set the `password` environment variable in [database.properties](database.properties) with your password

### Run the Code

1. Run the [Driver.java](src/main/driver/Driver.java) file to run the program.
