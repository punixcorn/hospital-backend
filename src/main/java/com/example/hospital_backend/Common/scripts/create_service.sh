#!/bin/bash

# This script generates a Spring Data JPA repository interface file for specified folders.
# It assumes a directory structure where each folder name corresponds to an entity name.

# --- Configuration ---

# Define the root package for your Spring Boot application.
ROOT_PACKAGE="com.example.hospital_backend"

# Define the folders (entities) for which to create repositories.
# You can add or remove names from this array.
declare -a FOLDERS=("AssessmentInfo" "AssignedDriver" "AssignedNurse" "Certification" "ClientMedication" "ClientNurseHistory" "Document" "Driver"  "HospitalBackendApplication".java "HourlyData"  "Item" "Medication" "Note" "NurseCertification" "NurseSpecialization" "NurseWorkDay" "PreviousClient" "Specialization" "Task" "WorkDay" )

# Check if the folders array is empty.
if [ ${#FOLDERS[@]} -eq 0 ]; then
    echo "No folders specified. Please edit the FOLDERS array in the script."
    exit 1
fi

# Loop through each folder name in the array.
for FOLDER_NAME in "${FOLDERS[@]}"; do
    # Create the filename for the repository interface.
    FILE_NAME="${FOLDER_NAME}Service.java"

    # Define the file path relative to the current working directory.
    FILE_PATH="${FOLDER_NAME}/${FILE_NAME}"

    # Check if the folder exists.
    if [ ! -d "$FOLDER_NAME" ]; then
        echo "Warning: Folder '$FOLDER_NAME' not found. Skipping."
        continue
    fi

    # Check if the file already exists to prevent overwriting.
    if [ -e "$FILE_PATH" ]; then
        rm "$FILE_PATH"
        echo "Warning: File '$FILE_PATH' already exists. DELETREING"
    fi

    echo "Generating Service for '$FOLDER_NAME' at '$FILE_PATH'..."

    # Create the file and write the content to it.
    # The 'cat << EOF' is a here document, which allows for multi-line string input.
    cat > "$FILE_PATH" << EOF
package ${ROOT_PACKAGE}.${FOLDER_NAME};


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${FOLDER_NAME}Service {

    @Autowired
    ${FOLDER_NAME}Repository ${FOLDER_NAME:l}Repository;

    public Optional<${FOLDER_NAME}> get${FOLDER_NAME}ById(Long id) {
        return ${FOLDER_NAME}Repository.findById(id);
    }

    public List<${FOLDER_NAME}> getAll${FOLDER_NAME}s() {
        return ${FOLDER_NAME}Repository.findAll();
    }

    public ${FOLDER_NAME} save${FOLDER_NAME}(${FOLDER_NAME} ${FOLDER_NAME:l}) {
        return ${FOLDER_NAME:l}Repository.save(${FOLDER_NAME});
    }

    public void delete${FOLDER_NAME}(Long id) {
        ${FOLDER_NAME:l}Repository.deleteById(id);
    }

}
EOF

    echo "Successfully created '$FILE_PATH'."

done

echo "Script finished."



