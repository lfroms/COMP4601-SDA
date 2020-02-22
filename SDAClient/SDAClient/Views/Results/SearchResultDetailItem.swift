//
//  SearchResultListItem.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct SearchResultDetailItem<Content>: View where Content: View {
    let label: String
    let content: () -> Content

    init(label: String, @ViewBuilder detail: @escaping () -> Content) {
        self.label = label
        self.content = detail
    }

    var body: some View {
        HStack {
            Text(label)
            Spacer()
            content()
                .foregroundColor(.secondary)
        }
    }
}

struct SearchResultDetailItem_Previews: PreviewProvider {
    static var previews: some View {
        SearchResultDetailItem(label: "Name") {
            EmptyView() 
        }
    }
}
