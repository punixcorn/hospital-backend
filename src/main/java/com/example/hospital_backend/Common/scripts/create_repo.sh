#!/bin/bash

# This script generates a Spring Data JPA repository interface file for specified folders.
# It assumes a directory structure where each folder name corresponds to an entity name.

# --- Configuration ---

# Define the root package for your Spring Boot application.
ROOT_PACKAGE="com.example.hospital_backend"

# Define the folders (entities) for which to create repositories.
# You can add or remove names from this array.
declare -a FOLDERS=("AssessmentInfo" "AssignedDriver" "AssignedNurse" "Certification" "Client" "ClientMedication" "ClientNurseHistory" "Document" "Driver" "Exception" "HospitalBackendApplication".java "HourlyData" "Initializers" "Item" "Medication" "Note" "NurseCertification" "NurseSpecialization" "NurseWorkDay" "PreviousClient" "Specialization" "Task" "WorkDay" )

# Check if the folders array is empty.
if [ ${#FOLDERS[@]} -eq 0 ]; then
    echo "No folders specified. Please edit the FOLDERS array in the script."
    exit 1
fi

# Loop through each folder name in the array.
for FOLDER_NAME in "${FOLDERS[@]}"; do
    # Create the filename for the repository interface.
    FILE_NAME="${FOLDER_NAME}Repository.java"

    # Define the file path relative to the current working directory.
    FILE_PATH="${FOLDER_NAME}/${FILE_NAME}"

    # Check if the folder exists.
    if [ ! -d "$FOLDER_NAME" ]; then
        echo "Warning: Folder '$FOLDER_NAME' not found. Skipping."
        continue
    fi

    # Check if the file already exists to prevent overwriting.
    if [ -e "$FILE_PATH" ]; then
        echo "Warning: File '$FILE_PATH' already exists. Skipping."
        continue
    fi

    echo "Generating repository for '$FOLDER_NAME' at '$FILE_PATH'..."

    # Create the file and write the content to it.
    # The 'cat << EOF' is a here document, which allows for multi-line string input.
    cat > "$FILE_PATH" << EOF
package ${ROOT_PACKAGE}.${FOLDER_NAME};

import org.springframework.data.jpa.repository.JpaRepository;

public interface ${FOLDER_NAME}Repository extends JpaRepository<${FOLDER_NAME}, Long> {

}
EOF

    echo "Successfully created '$FILE_PATH'."

done

echo "Script finished."
