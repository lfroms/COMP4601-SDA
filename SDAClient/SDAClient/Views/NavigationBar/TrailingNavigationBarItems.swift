//
//  TrailingNavigationBarItems.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct TrailingNavigationBarItems: View {
    @EnvironmentObject private var appState: AppState

    var body: some View {
        HStack {
            SystemActivityIndicator(animating: $appState.loading, style: .medium)
            SettingsSheetToggleButton()
        }
    }
}

struct TrailingNavigationBarItems_Previews: PreviewProvider {
    static var previews: some View {
        TrailingNavigationBarItems()
            .environmentObject(AppState())
    }
}
