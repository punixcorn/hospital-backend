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
    FILE_NAME="${FOLDER_NAME}Controller.java"

    # Define the file path relative to the current working directory.
    FILE_PATH="${FOLDER_NAME}/${FILE_NAME}"

    # Check if the folder exists.
    if [ ! -d "$FOLDER_NAME" ]; then
        echo "Warning: Folder '$FOLDER_NAME' not found. Skipping."
        continue
    fi

    # Check if the file already exists to prevent overwriting.
    if [ -e "$FILE_PATH" ]; then
        echo "Warning: File '$FILE_PATH' already exists. continue..."
        continue
    fi

    echo "Generating Controller for '$FOLDER_NAME' at '$FILE_PATH'..."

    # Create the file and write the content to it.
    # The 'cat << EOF' is a here document, which allows for multi-line string input.
    cat > "$FILE_PATH" << EOF
package ${ROOT_PACKAGE}.${FOLDER_NAME};

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_backend.Certification.Certification;
import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.PreviousClient.PreviousClient;
import com.example.hospital_backend.Specialization.Specialization;
import com.example.hospital_backend.*;

@RestController
@RequestMapping("/api/${FOLDER_NAME:l}s")
@CrossOrigin(origins = "*")
public class ${FOLDER_NAME}Controller {

    @Autowired
    private ${FOLDER_NAME}Service ${FOLDER_NAME:l}Service;

    // Get all ${FOLDER_NAME:l}s
    @GetMapping
    public ResponseEntity<List<${FOLDER_NAME}>> getAll${FOLDER_NAME}s() {
        List<${FOLDER_NAME}> ${FOLDER_NAME:l}s = ${FOLDER_NAME:l}Service.getAll${FOLDER_NAME}s();
        return ResponseEntity.ok(${FOLDER_NAME:l}s);
    }

    // Get ${FOLDER_NAME:l} by ID
    @GetMapping("/{id}")
    public ResponseEntity<${FOLDER_NAME}> get${FOLDER_NAME}ById(@PathVariable Long id) {
        Optional<${FOLDER_NAME}> ${FOLDER_NAME:l} = ${FOLDER_NAME:l}Service.get${FOLDER_NAME}ById(id);
        return ${FOLDER_NAME:l}.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new ${FOLDER_NAME:l}
    @PostMapping("/create")
    public ResponseEntity<${FOLDER_NAME}> create${FOLDER_NAME}(@RequestBody ${FOLDER_NAME} ${FOLDER_NAME:l}) {
        try {
            ${FOLDER_NAME} saved${FOLDER_NAME} = ${FOLDER_NAME:l}Service.save${FOLDER_NAME}(${FOLDER_NAME:l});
            return ResponseEntity.status(HttpStatus.CREATED).body(saved${FOLDER_NAME});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing ${FOLDER_NAME:l}
    @PutMapping("/{id}")
    public ResponseEntity<${FOLDER_NAME}> update${FOLDER_NAME}(@PathVariable Long id, @RequestBody ${FOLDER_NAME} ${FOLDER_NAME:l}) {
        Optional<${FOLDER_NAME}> existing${FOLDER_NAME} = ${FOLDER_NAME:l}Service.get${FOLDER_NAME}ById(id);
        if (existing${FOLDER_NAME}.isPresent()) {
            ${FOLDER_NAME:l}.setId(id); // Ensure the ID is set correctly
            ${FOLDER_NAME} updated${FOLDER_NAME} = ${FOLDER_NAME:l}Service.save${FOLDER_NAME}(${FOLDER_NAME:l});
            return ResponseEntity.ok(updated${FOLDER_NAME});
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a ${FOLDER_NAME:l}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete${FOLDER_NAME}(@PathVariable Long id) {
        Optional<${FOLDER_NAME}> existing${FOLDER_NAME} = ${FOLDER_NAME:l}Service.get${FOLDER_NAME}ById(id);
        if (existing${FOLDER_NAME}.isPresent()) {
            ${FOLDER_NAME:l}Service.delete${FOLDER_NAME}(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
EOF

    echo "Successfully created '$FILE_PATH'."

done

echo "Script finished."



