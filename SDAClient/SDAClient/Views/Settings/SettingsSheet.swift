//
//  SettingsSheet.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct SettingsSheet: View {
    @EnvironmentObject private var appState: AppState

    var body: some View {
        NavigationView {
            VStack {
                HStack(alignment: .firstTextBaseline) {
                    Text("Host address:")
                        .foregroundColor(.secondary)
                    TextField("localhost:8080", text: $appState.hostAddress)
                        .autocapitalization(.none)
                        .disableAutocorrection(true)
                }

                Spacer()
            }
            .padding(16)
            .navigationBarTitle("Settings", displayMode: .inline)
            .navigationBarItems(trailing:
                Button(action: handleClose) {
                    Text("Close")
                }
            )
        }
    }

    private func handleClose() {
        appState.showingSettingsSheet.toggle()
    }
}

struct SettingsSheet_Previews: PreviewProvider {
    static var previews: some View {
        SettingsSheet()
            .environmentObject(AppState())
    }
}
