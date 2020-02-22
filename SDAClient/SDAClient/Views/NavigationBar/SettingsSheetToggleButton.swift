//
//  SettingsSheetToggleButton.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct SettingsSheetToggleButton: View {
    @EnvironmentObject private var appState: AppState

    var body: some View {
        Button(action: handleAction) {
            Image(systemName: "gear")
        }
    }

    private func handleAction() {
        appState.showingSettingsSheet.toggle()
    }
}

struct SettingsSheetToggleButton_Previews: PreviewProvider {
    static var previews: some View {
        SettingsSheetToggleButton()
            .environmentObject(AppState())
    }
}
